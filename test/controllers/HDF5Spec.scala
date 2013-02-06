package controllers

import org.specs2.mutable.{Around, Specification}
import play.api.mvc.Result
import org.specs2.execute.AsResult
import lib.hdf5_getters

class HDF5Spec extends Specification{
  "An hdf5spec" should {
    "be able to read an artist " in {
      println("coucou"+System.getProperty("java.library.path"))
      val file = hdf5_getters.hdf5_open_readonly("/Users/jean/Downloads/MillionSongSubset/data/A/A/A/TRAAAAW128F429D538.h5")
      val artist_id = hdf5_getters.get_artist_id(file)
      val artist_name = hdf5_getters.get_artist_name(file)
      println(artist_name)
      artist_id must be_==("ARD7TVE1187B99BFB1")
      1==1
    }
  }
}
