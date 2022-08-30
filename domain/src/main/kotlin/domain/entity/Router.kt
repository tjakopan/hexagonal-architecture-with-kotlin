package domain.entity

import domain.vo.*

sealed class Router(id: Id, vendor: Vendor, model: Model, ip: IP, location: Location) :
  Equipment(id, vendor, model, ip, location) {
  companion object {
    inline fun <reified T> typePredicate(): (Router) -> Boolean = { it is T }

    fun modelPredicate(model: Model): (Router) -> Boolean = { it.model == model }

    fun countryPredicate(location: Location): (Router) -> Boolean = { it.location.country == location.country }
  }
}