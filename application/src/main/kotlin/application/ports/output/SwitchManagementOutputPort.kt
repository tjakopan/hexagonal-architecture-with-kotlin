package application.ports.output

import domain.entity.Switch
import domain.vo.Id

interface SwitchManagementOutputPort {
  fun retrieveSwitch(id: Id): Switch?
}