class HomeController < ApplicationController
  def index
    @jibbers = Jibber.all
  end
end
