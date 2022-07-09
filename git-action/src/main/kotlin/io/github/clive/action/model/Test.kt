package io.github.clive.action.model

import javax.persistence.*

//@Entity
class Drivers {
    @Id
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @ManyToMany(mappedBy = "drivers")
    private val cars: List<Cars>? = null
}

//@Entity
class Cars {

    @Id
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @ManyToMany
    @JoinTable(
        name = "drivers_cars_l",
        joinColumns = [JoinColumn(
            name = "car_id",
            referencedColumnName = "id",
            foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
        )],
        inverseJoinColumns = [JoinColumn(
            name = "driver_id",
            referencedColumnName = "id",
            foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
        )],
    )
    private val drivers: List<Drivers>? = null
}

