package framework.adapters.output.h2.data

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "location")
class LocationData(
  @Column(name = "address") val address: String?,
  @Column(name = "city") val city: String?,
  @Column(name = "state") val state: String?,
  @Column(name = "zip_code") val zipCode: Int?,
  @Column(name = "country") val country: String?,
  @Column(name = "latitude") val latitude: Float?,
  @Column(name = "longitude") val longitude: Float?,
  @Id @Column(name = "id") val id: Int? = null
) {
}