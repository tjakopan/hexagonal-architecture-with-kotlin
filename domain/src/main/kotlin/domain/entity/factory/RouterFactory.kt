package domain.entity.factory

import domain.entity.CoreRouter
import domain.entity.EdgeRouter
import domain.entity.Router
import domain.vo.*
import kotlin.reflect.KClass

object RouterFactory {
  @Suppress("UNCHECKED_CAST")
  fun <T : Router> createRouter(
    vendor: Vendor,
    model: Model,
    ip: IP,
    location: Location,
    type: KClass<T>,
    id: Id? = null
  ): T =
    when (type) {
      CoreRouter::class -> CoreRouter(id ?: Id(), vendor, model, ip, location) as T
      EdgeRouter::class -> EdgeRouter(id ?: Id(), vendor, model, ip, location) as T
      else -> throw IllegalArgumentException("Unknown router type $type.")
    }
}

inline fun <reified T : Router> RouterFactory.createRouter(
  vendor: Vendor,
  model: Model,
  ip: IP,
  location: Location,
  id: Id? = null
): T =
  createRouter(vendor, model, ip, location, T::class, id)