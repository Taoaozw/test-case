package io.github.akka.demo.actor

import org.springframework.context.*
import org.springframework.data.jpa.repository.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*
import java.lang.Thread.*
import javax.persistence.*


@Service
class TestService(val repo:DaRepository) : ApplicationContextAware {

    lateinit var context: ApplicationContext




    fun hello() {
//        context.getBean(TestService::class.java).naaa()
        //println("sleep start")
        //naaa()
        naaa()
    }

    @Transactional
    fun naaa() {
        repo.saveAndFlush(Da(name = "test"))
        repo.saveAndFlush(Da(name = "dsadasda"))
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        context = applicationContext
    }
}

@Entity
class Da(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long =0,

    val name:String
)


@Service
interface DaRepository : JpaRepository<Da, Long>
