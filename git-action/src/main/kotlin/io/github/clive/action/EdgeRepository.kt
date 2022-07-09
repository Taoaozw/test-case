package io.github.clive.action

import io.github.clive.action.model.*
import org.springframework.data.neo4j.repository.*
import org.springframework.data.neo4j.repository.query.*
import org.springframework.stereotype.*


@Repository
interface EdgeRepository : Neo4jRepository<INode, Long> {

    fun findByNameAndId(name: String, id: Long): INode?

    @Query("MATCH (n:Edge)-[r]->(m:Edge) RETURN n,r,m")
    fun grah(): List<Any>
}