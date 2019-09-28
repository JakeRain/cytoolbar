package com.algorithm

import java.lang.IllegalArgumentException
import java.lang.NullPointerException
import java.lang.RuntimeException
import java.lang.reflect.*
import java.util.*
import kotlin.NoSuchElementException


val EMPTY_TYPE_ARRAY = arrayListOf<Type>()


fun methodError(
    method: Method,
    message: String,
    vararg args: Any,
    cause: Throwable? = null
): RuntimeException {
    val msg = String.format(message, args)
    return IllegalArgumentException(
        "${msg}\n  for method ${method.declaringClass.simpleName}.${method.name}",
        cause
    )
}


fun parameterError(
    method: Method,
    cause: Throwable? = null,
    p: Int,
    message: String,
    vararg args: Any
): RuntimeException {
    return methodError(
        method,
        cause = cause,
        message = "${message} (parameter #${p + 1})",
        args = *arrayOf(args)
    )
}

fun getRawType(type: Type): Class<*> {
    Objects.requireNonNull(type, "type == null")

    if (type is Class<*>) {
        // Type is a normal class.
        return type as Class<*>
    }
    if (type is ParameterizedType) {
        val parameterizedType = type as ParameterizedType

        // I'm not exactly sure why getRawType() returns Type instead of Class. Neal isn't either but
        // suspects some pathological case related to nested classes exists.
        val rawType = parameterizedType.getRawType()
        if (rawType !is Class<*>) throw IllegalArgumentException()
        return rawType as Class<*>
    }
    if (type is GenericArrayType) {
        val componentType = (type as GenericArrayType).getGenericComponentType()
        return java.lang.reflect.Array.newInstance(getRawType(componentType), 0)::class.java
    }
    if (type is TypeVariable<*>) {
        // We could use the variable's bounds, but that won't work if there are multiple. Having a raw
        // type that's more general than necessary is okay.
        return Any::class.java
    }
    if (type is WildcardType) {
        return getRawType((type as WildcardType).getUpperBounds()[0])
    }

    throw IllegalArgumentException(
        "Expected a Class, ParameterizedType, or "
                + "GenericArrayType, but <" + type + "> is of type " + type.javaClass.name
    )
}


fun equals(a :Type , b : Type):Boolean{
    if(a === b){
        return true
    }
    else if(a is Class<*> ){
        return a== b
    }else if(a is ParameterizedType){
        if(!(b is ParameterizedType))return false
        val pa = a as ParameterizedType
        val pb = b as ParameterizedType
        val ownerA = pa.ownerType
        val ownerB = pb.ownerType
        return (ownerA == ownerB || (ownerA != null && ownerA.equals(ownerB)))
                && pa.rawType.equals(pb.rawType)
                && Arrays.equals(pa.actualTypeArguments , pb.actualTypeArguments)
    }else if(a is GenericArrayType){
        if(!(b is GenericArrayType))return false
        val ga = a as GenericArrayType
        val gb = b as GenericArrayType
        return equals(ga.genericComponentType, gb.genericComponentType)
    }else if(a is WildcardType){
        if(!(b is WildcardType))return false
        val wa = a as WildcardType
        val wb = b as WildcardType
        return Arrays.equals(wa.upperBounds , wb.upperBounds)
                &&Arrays.equals(wa.lowerBounds , wb.lowerBounds)
    }else if(a is TypeVariable<*>){
        if(!(b is TypeVariable<*>))return false
        val va = a as TypeVariable<*>
        val vb = b as TypeVariable<*>
        return va.genericDeclaration == vb.genericDeclaration
                && va.name == vb.name
    }else{
        return false
    }
}

/**
 * Returns the generic supertype for 'supertype' . For example, given a class 'IntegerSet', the result for when supertype is 'Set.class' is 'Set<Integer>' and the
 * result when the supertype is 'Collection.class' is 'Collection<Integer>'
 */
fun getGenericSuperType(context: Type? , rawType:Class<*> , toResolve: Class<*>):Type?{
    var rawType = rawType
    if(toResolve == rawType)return context

    //we skip searching through interfaces if unknown is an interface
    if(toResolve.isInterface){
        val interfaces = rawType.interfaces
        interfaces.forEachIndexed { i, clazz ->
            if(interfaces[i] == toResolve){
                return rawType.genericInterfaces[i]
            }else if (toResolve.isAssignableFrom(interfaces[i])){
                return getGenericSuperType(
                    rawType.genericInterfaces[i],
                    interfaces[i],
                    toResolve
                )
            }
        }
    }

    if(!rawType.isInterface){
        while(rawType !== Any::class.java){
            val rawSupertype = rawType.superclass
            if(rawSupertype == toResolve){
                return rawType.genericSuperclass
            }else if(toResolve.isAssignableFrom(rawSupertype!!)){
                return getGenericSuperType(
                    rawType.genericSuperclass,
                    rawSupertype,
                    toResolve
                )
            }
            rawType = rawSupertype
        }
    }
    return toResolve
}
private fun indexOf(array:Array<*> , toFind : Any):Int{
    array.forEachIndexed { index, any ->
        if(toFind.equals(any))return index
    }


    throw NoSuchElementException()
}


fun typeToString(type: Type):String{
    val types = Array<Type>(0) {
        type
    }
    return if(type is Class<*>)(type as Class<*>).name else type.toString()
}

//fun getSupertype(context: Type? , contextRawType:Class<*> , supertype:Class<*>):Type{
//    if(!supertype.isAssignableFrom(contextRawType))throw IllegalStateException()
//
//
//}
fun resolve(context : Type , contextRawType: Class<*> , toResolve: Type):Type{
    var toResolve = toResolve
    while(true){
        if(toResolve is TypeVariable<*>){
            val typeVariable = toResolve as TypeVariable<*>
            toResolve = resolveTypeVariable(context, contextRawType, typeVariable)
            if(toResolve == typeVariable){
                return toResolve
            }

        }else if(toResolve is Class<*> &&(toResolve as Class<*>).isArray){
            val original = toResolve as Class<*>
            val componentType = original.componentType as Type
            val newComponentType = resolve(context, contextRawType, componentType)
//            return if(componentType == newComponentType) original else GenericArrayType
        }
    }
}

fun resolveTypeVariable(context: Type , contextRawType: Class<*> , unknown: TypeVariable<*>):Type{
    val declaredByRaw = declaringClassOf(unknown)

    if(declaredByRaw ==null) return unknown

    val declaredBy = getGenericSuperType(context, contextRawType, declaredByRaw)
    if(declaredBy is ParameterizedType){
        val index = indexOf(declaredByRaw.typeParameters, unknown)
        return (declaredBy as ParameterizedType).actualTypeArguments[index]
    }
    return unknown
}

private fun declaringClassOf(typeVariable: TypeVariable<*>):Class<*>?{
    val genericDeclaration = typeVariable.genericDeclaration
    return if(genericDeclaration is Class<*>) genericDeclaration else null
}

fun checkNotPrimitive(type:Type?){
    if(type is Class<*> && (type as Class<*>).isPrimitive){
        throw IllegalArgumentException()
    }
}




class GenericArrayTypeImpl : GenericArrayType{
    val componentType : Type
    constructor(componentType: Type){
        this.componentType = componentType
    }

    override fun getGenericComponentType(): Type  = componentType

    override fun equals(other: Any?): Boolean =
         other is GenericArrayType
                && equals(this, other as GenericArrayType)


    override fun hashCode(): Int = componentType.hashCode()

    override fun toString(): String = "${typeToString(componentType)}[]"

}


class WildcardTypeImpl : WildcardType{
    private val upperBound:Type
    private val lowerBound : Type?
    constructor(unpperBounds : Array<*> , lowerBounds : Array<*>){
        if(lowerBounds.size > 1)throw java.lang.IllegalArgumentException()
        if(upperBounds.size != 1)throw java.lang.IllegalArgumentException()
        if(lowerBounds.size == 1){
            if(lowerBounds[0] == null) throw java.lang.IllegalArgumentException()
            checkNotPrimitive(lowerBounds[0] as Type)
            if(upperBounds[0] != Any::class.java) throw java.lang.IllegalArgumentException()
            this.lowerBound = lowerBounds[0] as Type
            this.upperBound = Any::class.java
        }else{
            if(upperBounds[0] == null)throw NullPointerException()
            checkNotPrimitive(upperBounds[0])
            this.lowerBound = null
            this.upperBound = upperBounds[0]
        }
    }

    override fun getUpperBounds(): Array<Type> = Array<Type>(1){
        upperBound
    }

    override fun getLowerBounds(): Array<Type?> = Array(1){lowerBound}

    override fun equals(other: Any?): Boolean = other is WildcardType && equals(
        this,
        other as WildcardType
    )

    override fun hashCode(): Int {
        var result = if(lowerBound != null )31 + lowerBound.hashCode() else 1
        var size = 31 + upperBound.hashCode()
        return result xor size
    }



}





































