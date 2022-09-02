package framework.adapters.output.h2.data

import jakarta.persistence.*
import org.eclipse.persistence.annotations.Convert
import org.eclipse.persistence.annotations.Converter
import java.util.*

@Entity
@Table(name = "switch")
@Converter(name = "uuidConverter", converterClass = UuidConverter::class)
class SwitchData(
  @Id @Column(name = "id", columnDefinition = "uuid", updatable = false) @Convert("uuidConverter") val id: UUID,
  @Column(name = "router_id") @Convert("uuidConverter") val routerId: UUID,
  @Embedded @Enumerated(EnumType.STRING) @Column(name = "vendor") val vendor: VendorData,
  @Embedded @Enumerated(EnumType.STRING) @Column(name = "model") val model: ModelData,
  @Embedded @Enumerated(EnumType.STRING) @Column(name = "type") val type: SwitchTypeData,
  @OneToMany
  @JoinColumn(table = "network", name = "switch_id", referencedColumnName = "id")
  val networks: List<NetworkData>,
  @Embedded
  @AttributeOverride(name = "address", column = Column(name = "ip_address"))
  @AttributeOverride(name = "protocol", column = Column(name = "ip_protocol"))
  val ip: IPData,
  @ManyToOne @JoinColumn(name = "location_id", referencedColumnName = "id") val location: LocationData
)