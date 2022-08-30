package domain.entity.specification

import domain.entity.Equipment
import domain.entity.specification.shared.AbstractSpecification
import domain.entity.specification.shared.check

class DifferentIpSpec(private val equipment: Equipment) : AbstractSpecification<Equipment>() {
  override fun isSatisfiedBy(t: Equipment): Boolean = equipment.ip != t.ip

  override fun check(t: Equipment) = check(t) { "It's not possible to attach routers with same IP." }
}