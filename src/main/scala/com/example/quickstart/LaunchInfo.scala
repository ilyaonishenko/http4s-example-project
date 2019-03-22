package com.example.quickstart

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._

case class LaunchInfo(flight_number: Int, mission_name: String, mission_id: List[String], launch_year: String,
                      launch_date_unix: Long, launch_date_utc: String, launch_date_local: String, is_tentative: Boolean,
                      tentative_max_precision: String, tbd: Boolean, launch_window: Int, rocket: Rocket, ships: List[String],
                      links: Links, upcoming: Boolean, static_fire_date_utc: String)

case class Rocket(rocket_id: String, rocket_name: String, rocket_type: String)

case class Links(mission_patch: String, mission_patch_small: String, reddit_launch: String, reddit_media: String, presskit: String, article_link: String, wikipedia: String,
                 video_link: String, youtube_id: String, flickr_images: List[String])


object LaunchInfo {
  implicit val launchInfoDecoder: Decoder[LaunchInfo] = deriveDecoder
  implicit val launchInfoEncoder: Encoder[LaunchInfo] = deriveEncoder
  implicit val rocketDecoder: Decoder[Rocket] = deriveDecoder
  implicit val rocketEncoder: Encoder[Rocket] = deriveEncoder
  implicit val linksDecoder: Decoder[Links] = deriveDecoder
  implicit val linksEncoder: Encoder[Links] = deriveEncoder
}
