package io.github.clive.action.model

import org.hibernate.*
import javax.persistence.*


//@Entity
//@Cacheable
data class Robot(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    var name: String,

    ) {

//    @field:OneToOne(cascade = [CascadeType.ALL])
//    @field:JoinColumn(name = "parent_id", foreignKey = ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
//    lateinit var parent: Robot


    @ManyToMany(cascade = [CascadeType.ALL], mappedBy = "following")
    private val extendingRobots: Collection<Robot> = listOf()

    @JoinTable(
        name = "followers",
        joinColumns = [JoinColumn(name = "ex_rtobots_id")],
        inverseJoinColumns = [JoinColumn(name = "follower_id")]
    )
    @ManyToMany(cascade = [CascadeType.ALL])
    private val following: Collection<Robot> = listOf()

}