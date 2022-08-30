package domain.service

import domain.entity.Switch
import domain.vo.Id

object SwitchService {
  fun filterAndRetrieveSwitches(switches: List<Switch>, predicate: (Switch) -> Boolean): List<Switch> =
    switches.filter(predicate)

  fun findById(switches: Map<Id, Switch>, id: Id): Switch? = switches[id]
}