var module = angular.module('GithubController', []);

module.controller('github', function($scope, $http) {
    $scope.getDetails = function() {
      $http({
          method : "GET",
          url : "/repositories/" + $scope.username + "/" + $scope.repository
      }).then(function success(response) {
         $scope.response = response.data;
      }, function error(response) {
          $scope.response = response.data;
      });
    }

});