class Jibber < ActiveRecord::Base
  has_many :jabbers, class_name: "Jibber", foreign_key: "jibber_id"
  belongs_to :jibber, class_name:"Jibber"  
  belongs_to :user

  validates :text, :presence => true
  validates :jtype, :presence => true
  validates :jtype, :inclusion => %w(jibber jabber)
  
  after_validation :duplicate_jibber

  scope :jibbers, lambda { where(:jtype => "jibber").order("updated_at DESC") }
  scope :jabbers, lambda { where(:jtype => "jabber") }
  
  def duplicate_jibber
    @jibbers=Jibber.where(:text => self.text).all
    @jibbers.each do |temp|
      errors.add(:text,"similar jibber was recently posted") unless (Time.now-temp.updated_at)/60 > 15
    end
  end
  
end
