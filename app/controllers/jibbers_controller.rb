class JibbersController < ApplicationController
  def index
    @jibbers=Jibber.all
  end

  def new 
    @jibber=Jibber.new
  end

  def create
    @jibber=Jibber.new(params[:jibber])
    if(!@jibber.save)
      puts "..............not saved"
      render :action => :new
    end
  end

  def delete
  end

  def edit
  end

  def update
  end

end
