package application.ports.output

import domain.entity.Router
import domain.vo.Id

interface RouterManagementOutputPort {
  fun retrieveRouter(id: Id): Router?

  fun persistRouter(router: Router): Router
}