class UserRegisterController < ApplicationController
  def new 
    @user = User.new
    @jibbers=Jibber.all
  end

  def create
    @user=User.new(params[:user])
    @jibbers=Jibber.all
    if(@user.save)
       upload
       puts "..............saved"
    else
      render "user_register/new"
    end
  end
  
  def upload
    uploaded_io = params[:user][:image]
    name = "image_" << @user.username << uploaded_io.original_filename
    File.open(Rails.root.join('public', 'images','profile',name ), 'w') do |file|
      file.write(uploaded_io.read)
    end
  end
  
  def show
    @jibbers=Jibber.all
    @user = User.find(params[:id])
  end
end
