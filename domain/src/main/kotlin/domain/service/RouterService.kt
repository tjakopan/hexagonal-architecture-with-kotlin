package domain.service

import domain.entity.Router
import domain.vo.Id

object RouterService {
  fun filterAndRetrieveRouters(routers: List<Router>, predicate: (Router) -> Boolean): List<Router> =
    routers.filter(predicate)

  fun findById(routers: Map<Id, Router>, id: Id): Router? = routers[id]
}