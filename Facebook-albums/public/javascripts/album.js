/**
 * Created by Wensheng on 15/9/22.
 */
var albumApp = angular.module('albumApp', []);

albumApp.controller('AlbumController', ['$http','$scope', function($http, $scope){

    $scope.albumPhotoList=[];
    $scope.albumList =[];
    $scope.selectedAlbum = {};
    $http.get('/albums').then(function(response) {
        console.log(response);
        $scope.albumList = response.data;
    }, function(response) {
        console.log(response);
    });

    this.selectAlbum = function (id){
        var selectedAlbum = "";
        angular.forEach($scope.albumList, function(album, index){
            if(album.id === id){
                $scope.selectedAlbum = album;
            }else{
                return;
            }
        });
        if($scope.selectedAlbum !== null && $scope.selectedAlbum !== undefined){
            this.getCorrespondedAlbum($scope.selectedAlbum);
        }
    }

    this.updateAlbum = function(){
        $http.post("/album?album="+JSON.stringify($scope.selectedAlbum)).then(
            function(response){
                console.log(response);
            },
            function(response){
                console.log(response);
            }
        );
    }
    this.selectPhoto = function(photo){
        console.log(photo);
        $scope.selectedPhoto = photo;
    }
    $scope.currentComment = "";

    this.submitComment = function(){
        $http.post("/comment?photoId="+$scope.selectedPhoto.id+"&comment="+$scope.currentComment)
            .then(function(response){
                console.log(response);
            },function(response){
                console.log(response);
        });
    }

    this.getCorrespondedAlbum = function(album){
        $http.get("/album/"+album.id, {id: album.id})
            .then(function(response){
                console.log(response.data);
                $scope.albumPhotoList = response.data;
                angular.forEach($scope.albumPhotoList, function(photo, index){
                    $http.get("/"+ photo.id+"/picture").then(
                        function(resp){
                            $scope.albumPhotoList[index].url = resp.data;
                        },
                        function(resp){

                        }
                    );
                });
            },function(response){
                console.log(response.data);
            });
    }



}]);