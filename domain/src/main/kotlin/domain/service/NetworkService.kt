package domain.service

import domain.vo.Network

object NetworkService {
  fun filterAndRetrieveNetworks(networks: List<Network>, predicate: (Network) -> Boolean): List<Network> =
    networks.filter(predicate)

  fun findNetwork(networks: List<Network>, predicate: (Network) -> Boolean): Network? =
    networks.find(predicate)
}