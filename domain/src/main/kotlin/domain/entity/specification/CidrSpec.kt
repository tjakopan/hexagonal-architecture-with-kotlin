package domain.entity.specification

import domain.entity.specification.shared.AbstractSpecification
import domain.entity.specification.shared.check

const val MINIMUM_ALLOWED_CIDR: Int = 8

class CidrSpec : AbstractSpecification<Int>() {
  override fun isSatisfiedBy(t: Int): Boolean = t >= MINIMUM_ALLOWED_CIDR

  override fun check(t: Int) = check(t) { "CIDR is below $MINIMUM_ALLOWED_CIDR." }
}