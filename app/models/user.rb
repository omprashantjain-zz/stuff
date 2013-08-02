require 'digest/sha2'

class User < ActiveRecord::Base
  has_many :jibbers 
  validates :firstname, :presence => true
  validates :lastname, :presence => true
  validates :username, :presence => true, :uniqueness => true,:length => 6..40
  validates_format_of :firstname,:lastname,:username, :with => /\A([a-zA-Z\s]+)\z/, :message => "Only alphabets are allowed."
  validates_presence_of :password_confirmation
  validates :password, :presence => true, :confirmation => true, :length => 6..20
  validates :email, :presence => true, :uniqueness => true
  validates_format_of :email,:with => /\A([^@\s]+)@((?:[-a-z0-9]+\.)+[a-z]{2,})\Z/i
  validates :dob, :presence => true
  validates :phone, :numericality => true,:length => 6..12
  validates :image, :presence => true
  attr_accessor :password_confirmation
  attr_reader :password

  validate :password_must_be_present
  validate :my_dob_validation
  before_validation :retrieve_image_url

  def retrieve_image_url
    imgid = User.last.present? ? User.last.id : 0
	self.image = "image_" << username << image.original_filename  if image.present?
  end

  def my_dob_validation
    errors.add(:dob,"not a valid format") unless  Date.parse(dob.to_s).instance_of?(Date)
    flag=errors.add(:dob,"Date of birth can not be in future") unless dob<Date.today
    if(!flag)
      errors.add(:dob,"Age of user must be atleast 18 Years") unless dob<(Date.today<<18*12)    # going back 18*12 months from current date
    end
  end

  def User.authenticate(username,password)
    if user =  find_by_username(username)
      if user.hashed_password == encrypt_password(password, user.salt)
        user
      end
    end
  end

  def User.encrypt_password(password,salt)
    Digest::SHA2.hexdigest(password + "wibble" + salt)
  end

  def password=(password)
    @password=password

    if password.present?
      generate_salt
      self.hashed_password=self.class.encrypt_password(password,salt)
    end
  end

  private
 
  def password_must_be_present
    errors.add(:password, "Missing password") unless hashed_password.present?
  end

  def generate_salt
    self.salt=self.object_id.to_s+rand.to_s
  end
end
