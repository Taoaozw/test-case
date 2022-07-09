package io.github.clive.action.model

import org.springframework.data.neo4j.core.schema.*
import java.util.UUID


@Node
data class INode(

    @Id
    @GeneratedValue
    var id: Long? = null,

    var name: String? = null,

    ) {

    @Relationship(direction = Relationship.Direction.OUTGOING)
    var edgs: MutableList<IEdge> = mutableListOf()
}

@RelationshipProperties
data class IEdge(

    @RelationshipId
    val id: Long? = null,

    val name: String? = UUID.randomUUID().toString(),

    @TargetNode
    val endNode: INode? = null,

    )