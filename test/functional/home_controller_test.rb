require 'test_helper'

class HomeControllerTest < ActionController::TestCase
  test "should get create" do
    get :create
    assert_response :success
  end

end
