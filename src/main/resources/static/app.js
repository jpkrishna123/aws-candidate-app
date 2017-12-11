(function () {
    var springBootAws = angular.module('SpringBootAwsDemo', ['ngRoute', 'angularUtils.directives.dirPagination']);

    springBootAws.directive('active', function ($location) {
        return {
            link: function (scope, element) {
                function makeActiveIfMatchesCurrentPath() {
                    if ($location.path().indexOf(element.find('a').attr('href').substr(1)) > -1) {
                        element.addClass('active');
                    } else {
                        element.removeClass('active');
                    }
                }

                scope.$on('$routeChangeSuccess', function () {
                    makeActiveIfMatchesCurrentPath();
                });
            }
        };
    });
    
    springBootAws.directive('fileModel', [ '$parse', function($parse) {
    	return {
    		restrict : 'A',
    		link : function(scope, element, attrs) {
    			var model = $parse(attrs.fileModel);
    			var modelSetter = model.assign;

    			element.bind('change', function() {
    				scope.$apply(function() {
    					modelSetter(scope, element[0].files[0]);
    				});
    			});
    		}
    	};
    } ]);
    
    springBootAws.controller('CreateCandidateCtrl', function ($scope, $location, $http) {
        var self = this;
        
        self.add = function () {            
        	var candidateModel = self.model;        	
        	
        	var formData = new FormData();
        	formData.append('firstName', candidateModel.firstName);
        	formData.append('lastName', candidateModel.lastName);
            formData.append('email', candidateModel.email);
            formData.append('phoneNumber', candidateModel.phoneNumber);
        	formData.append('resume', candidateModel.resume);
        		
        	$scope.saving=true;
        	$http.post('/spring-boot-aws/candidates', formData, {	
        	    transformRequest : angular.identity,
    			headers : {
    				'Content-Type' : undefined
    			}
    		}).success(function(savedCandidate) {
    			$scope.saving=false;
    			//$location.path("/view-candidate/" + savedCandidate.id);    			
    		}).error(function(data) {
    			$scope.saving=false; 
    		});
        };
    });
    
    
    
    springBootAws.controller('ViewAllCandidatesCtrl', function ($scope, $http) {
    	
    	var self = this;
    	$scope.candidates = []; 
    	$scope.searchText;
        
        $scope.dataLoading = true;
        $http.get('/spring-boot-aws/candidates').then(function mySucces(response) {
        	$scope.candidates = response.data;
        	$scope.dataLoading = false;
        }, function myError(response) {
        	$scope.candidate = response.statusText;
        	$scope.dataLoading = false;
        });        
        
        self.delete = function (candidateId) {
        	$scope.selectedCandidate = candidateId;
        	$scope.candidateDelete = true;
        	$http.delete('/spring-boot-aws/candidates/' + candidateId).then(function onSucces(response) {
            	$scope.candidates = _.without($scope.candidates, _.findWhere($scope.candidates, {id: candidateId}));
            	$scope.candidateDelete = false;
            }, function onError(){
            	
            });
        },
        
        $scope.searchFilter = function (obj) {
            var re = new RegExp($scope.searchText, 'i');
            return !$scope.searchText || re.test(obj.firstName) || re.test(obj.lastName.toString());
        };
    });
    
    springBootAws.filter('formatDate', function() {
    	return function(input) {
    		return moment(input).format("DD-MM-YYYY");
    	};
    });
    
    springBootAws.config(function ($routeProvider) {
        $routeProvider.when('/home', {templateUrl: 'pages/home.tpl.html'});
        $routeProvider.when('/create-candidate', {templateUrl: 'pages/createCandidate.tpl.html'});
        $routeProvider.when('/view-all-candidates', {templateUrl: 'pages/viewAllCandidates.tpl.html'});
        $routeProvider.otherwise({redirectTo: '/home'});
    });
    
}());