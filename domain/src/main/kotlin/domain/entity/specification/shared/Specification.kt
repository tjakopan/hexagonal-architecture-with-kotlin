package domain.entity.specification.shared

import domain.exception.GenericSpecificationException

interface Specification<T> {
  fun isSatisfiedBy(t: T): Boolean

  infix fun and(specification: Specification<T>): Specification<T>

  @Throws(GenericSpecificationException::class)
  fun check(t: T) = check(t) { "Failed requirement." }
}

@Throws(GenericSpecificationException::class)
inline fun <T> Specification<T>.check(t: T, lazyMessage: () -> Any) {
  if (!isSatisfiedBy(t)) {
    val message = lazyMessage()
    throw GenericSpecificationException(message.toString())
  }
}