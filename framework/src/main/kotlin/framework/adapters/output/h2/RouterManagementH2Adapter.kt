package framework.adapters.output.h2

import application.ports.output.RouterManagementOutputPort
import domain.entity.Router
import domain.vo.Id
import framework.adapters.output.h2.data.RouterData
import framework.adapters.output.h2.mappers.toData
import framework.adapters.output.h2.mappers.toDomain
import jakarta.persistence.EntityManager
import jakarta.persistence.Persistence
import jakarta.persistence.PersistenceContext

object RouterManagementH2Adapter : RouterManagementOutputPort {
  @PersistenceContext
  private val em: EntityManager = Persistence.createEntityManagerFactory("inventory")
    .run { createEntityManager() }

  override fun retrieveRouter(id: Id): Router? {
    val routerData = em.getReference(RouterData::class.java, id.id)
    return routerData?.toDomain()
  }

  override fun removeRouter(id: Id) {
    val routerData = em.getReference(RouterData::class.java, id.id)
    if (routerData != null) em.remove(routerData)
  }

  override fun persistRouter(router: Router) = em.persist(router.toData())
}