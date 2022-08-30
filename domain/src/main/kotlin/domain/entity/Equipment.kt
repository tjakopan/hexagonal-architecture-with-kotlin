package domain.entity

import domain.vo.*

sealed class Equipment(
  val id: Id,
  val vendor: Vendor,
  val model: Model,
  val ip: IP,
  val location: Location
) {
  companion object {
    fun vendorPredicate(vendor: Vendor): (Equipment) -> Boolean = { it.vendor == vendor }
  }
}