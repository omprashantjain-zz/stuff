class CreateJibbers < ActiveRecord::Migration
  def self.up
    create_table :jibbers, :primary_key => 'id' do |t|
      t.integer :id
      t.string  :text
      t.integer :user_id
      t.integer :jibber_id
      t.string  :type
      t.timestamps
    end
  end

  def self.down
    drop_table :jibbers
  end
end
