package framework.adapters.output.h2

import application.ports.output.SwitchManagementOutputPort
import domain.entity.Switch
import domain.vo.Id
import framework.adapters.output.h2.data.SwitchData
import framework.adapters.output.h2.mappers.toDomain
import jakarta.persistence.EntityManager
import jakarta.persistence.Persistence
import jakarta.persistence.PersistenceContext

object SwitchManagementH2Adapter : SwitchManagementOutputPort {
  @PersistenceContext
  private val em: EntityManager = Persistence.createEntityManagerFactory("inventory")
    .run { createEntityManager() }

  override fun retrieveSwitch(id: Id): Switch? {
    val switchData = em.getReference(SwitchData::class.java, id.id)
    return switchData?.toDomain()
  }
}