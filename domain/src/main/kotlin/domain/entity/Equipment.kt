package domain.entity

import domain.vo.*

sealed class Equipment(
  open val id: Id,
  open val vendor: Vendor,
  open val model: Model,
  open val ip: IP,
  open val location: Location
) {
  companion object {
    fun vendorPredicate(vendor: Vendor): (Equipment) -> Boolean = { it.vendor == vendor }
  }
}