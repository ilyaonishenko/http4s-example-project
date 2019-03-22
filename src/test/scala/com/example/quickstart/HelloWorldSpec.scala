package com.example.quickstart

import cats.effect.IO
import com.danielasfregola.randomdatagenerator.RandomDataGenerator
import org.http4s._
import org.http4s.client.blaze.Http1Client
import org.http4s.implicits._
import org.http4s.circe._

class HelloWorldSpec extends org.specs2.mutable.Specification with RandomDataGenerator {

  implicit val launchInfoEntityDecoder: EntityDecoder[IO, List[String]] = jsonOf[IO, List[String]]
  val randomLaunchInfo: LaunchInfo = random[LaunchInfo]
    .copy(flight_number = 23, links = Links.empty().copy(flickr_images = List("randomString")))
  val httpClient = Http1Client[IO]().unsafeRunSync

  println(randomLaunchInfo)

  "HelloWorld" >> {
    "successfully get random launch" >> {
      check(successfullyGetLaunch, Status.Ok, Some(randomLaunchInfo.links.flickr_images))
    }
  }

  def check(actual: IO[Response[IO]],
               expectedStatus: Status,
               expectedBody: Option[List[String]]): Boolean = {
    val actualResp         = actual.unsafeRunSync
    val statusCheck        = actualResp.status == expectedStatus
    val bodyCheck          = expectedBody match {
      case Some(expectedLaunchInfo) =>
        println(actualResp)
        actualResp.as[List[String]].unsafeRunSync() == expectedLaunchInfo
      case None => false
    }

    statusCheck && bodyCheck
  }

  val successLaunchInfo = new SpaceXClient[IO](httpClient) {
    override def getLaunchInfo(launchNum: Int): IO[LaunchInfo] = IO.pure(randomLaunchInfo)
  }

  private[this] val successfullyGetLaunch: IO[Response[IO]] = {
    val getLaunch = Request[IO](Method.GET, Uri.uri("/spacex_launches") / (randomLaunchInfo.flight_number).toString)
    println(getLaunch)
    new HelloWorldService[IO](successLaunchInfo).service.orNotFound.run(getLaunch)
  }
}
