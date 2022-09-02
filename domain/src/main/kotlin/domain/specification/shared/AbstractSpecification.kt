package domain.specification.shared

abstract class AbstractSpecification<T> : Specification<T> {
  override fun and(specification: Specification<T>): Specification<T> = AndSpecification(this, specification)
}