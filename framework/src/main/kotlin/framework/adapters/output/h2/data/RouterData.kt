package framework.adapters.output.h2.data

import jakarta.persistence.*
import org.eclipse.persistence.annotations.Convert
import org.eclipse.persistence.annotations.Converter
import java.util.*

@Entity
@Table(name = "router")
@Converter(name = "uuidConverter", converterClass = UuidConverter::class)
class RouterData(
  @Id @Column(name = "id", columnDefinition = "uuid", updatable = false) @Convert("uuidConverter") val id: UUID,
  @Column(name = "parent_core_id") @Convert("uuidConverter") val parentCoreId: UUID?,
  @Embedded @Enumerated(EnumType.STRING) @Column(name = "vendor") val vendor: VendorData,
  @Embedded @Enumerated(EnumType.STRING) @Column(name = "model") val model: ModelData,
  @Embedded
  @AttributeOverride(name = "address", column = Column(name = "ip_address"))
  @AttributeOverride(name = "protocol", column = Column(name = "ip_protocol"))
  val ip: IPData,
  @ManyToOne @JoinColumn(name = "location_id", referencedColumnName = "id") val location: LocationData,
  @Embedded @Enumerated(EnumType.STRING) @Column(name = "type") val type: RouterTypeData,
  @OneToMany
  @JoinColumn(table = "switch", name = "router_id", referencedColumnName = "id")
  val switches: List<SwitchData>,
  @OneToMany
  @JoinColumn(table = "router", name = "parent_core_id", referencedColumnName = "id")
  val routers: List<RouterData>
)