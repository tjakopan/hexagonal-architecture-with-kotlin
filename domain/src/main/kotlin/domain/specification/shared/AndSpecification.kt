package domain.specification.shared

import domain.exception.GenericSpecificationException

class AndSpecification<T>(private val spec1: Specification<T>, private val spec2: Specification<T>) :
  AbstractSpecification<T>() {
  override fun isSatisfiedBy(t: T): Boolean = spec1.isSatisfiedBy(t) && spec2.isSatisfiedBy(t)

  override fun check(t: T) {
    var e: GenericSpecificationException? = null
    try {
      spec1.check(t)
    } catch (e1: GenericSpecificationException) {
      e = e1
    }
    try {
      spec2.check(t)
    } catch (e2: GenericSpecificationException) {
      if (e != null) e.addSuppressed(e2)
      else e = e2
    }
    if (e != null) throw e
  }
}