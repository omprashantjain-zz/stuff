class UserSessionController < ApplicationController
  def new 
    @user = User.new
    @jibbers = Jibber.jibbers
  end

  def login
    user = User.where(:username => params[:user][:username]).first
    unless user
      flash[:notice] = "-->No User With This Username Found"
      redirect_to new_user_session_path
      return
    end
    if Digest::SHA2.hexdigest(params[:user][:password] + "wibble" + user.salt) == user.hashed_password
      cookies[:username] = user.username
    else
      flash[:notice] = "-->Wrong Password for the User"
      redirect_to new_user_session_path
      return
    end
  end

  def logout
	cookies[:username] = nil;
  end

end
