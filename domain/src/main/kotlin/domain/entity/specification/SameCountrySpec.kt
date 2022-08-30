package domain.entity.specification

import domain.entity.CoreRouter
import domain.entity.Equipment
import domain.entity.specification.shared.AbstractSpecification
import domain.entity.specification.shared.check

class SameCountrySpec(private val equipment: Equipment) : AbstractSpecification<Equipment>() {
  override fun isSatisfiedBy(t: Equipment): Boolean =
    if (t is CoreRouter) true
    else if (equipment.location.country != null && t.location.country != null) equipment.location.country == t.location.country
    else false

  override fun check(t: Equipment) = check(t) { "The equipment should be in the same country." }
}