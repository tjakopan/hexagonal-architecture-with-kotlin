package domain.specification

import domain.entity.Switch
import domain.specification.shared.AbstractSpecification
import domain.specification.shared.check

const val MAXIMUM_ALLOWED_NETWORKS = 6

class NetworkAmountSpec : AbstractSpecification<Switch>() {
  override fun isSatisfiedBy(t: Switch): Boolean = t.networks.size <= MAXIMUM_ALLOWED_NETWORKS

  override fun check(t: Switch) = check(t) { "The maximum number of networks is $MAXIMUM_ALLOWED_NETWORKS." }
}