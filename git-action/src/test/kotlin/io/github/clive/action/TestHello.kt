package io.github.clive.action

import io.github.clive.action.model.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.*
import org.springframework.data.neo4j.core.*

@SpringBootTest
class TestHello {

//    @Autowired
//    private lateinit var repo: RobotRepository

    @Autowired
    private lateinit var edgeRepo: EdgeRepository

    @Autowired
    private lateinit var client: Neo4jClient

    @Test
    fun list() {
//        val yu = Robot(name = "yu")
//        val shen = Robot(name = "shen")
//        shen.parent = yu
//        yu.parent = shen
//        repo.saveAllAndFlush(listOf(yu, shen))

    }

    @Test
    fun check() {
        //val r1 = repo.findByIdOrNull(1)
//        val r2 = repo.findByIdOrNull(2)!!
//        println("Hello r1: $r1")
//        r1.parent.name = "lol"
//        println("Hello r2: ${r2.name}")
    }

    @Test
    fun neo4j() {
        val node1 = INode(name = "tao")
        val node2 = INode(name = "su")
        val node3 = INode(name = "shen")
        val iEdge1 = IEdge( endNode =  node2)
        val iEdge2 = IEdge(endNode = node1)
        val iEdge3 = IEdge(endNode = node3)
        node1.edgs.add(iEdge1)
        node1.edgs.add(iEdge3)
        node2.edgs.add(iEdge2)
        edgeRepo.saveAll(listOf(node1))
    }

    @Test
    fun connect() {
//        val tao = edgeRepo.findByNameAndId("tao", 0)!!
//        val su = edgeRepo.findByNameAndId("su", 1)!!
//        tao.extEdges.add(su)
//        su.extEdges.add(tao)
//        edgeRepo.saveAll(listOf(su, tao))
        val tao = edgeRepo.findByNameAndId("tao", 3)!!
        println(tao)
    }


    @Test
    fun graph(){
        val maps = client
            .query("MATCH (n:Edge)-[r]->(m:Edge) RETURN n,m")
            .mappedBy{ _, record->
                val startNode = record.get("n").asNode()
                val endNode = record.get("m").asNode()
                startNode to endNode
            }
            .all()

        println(maps)
    }
}