/**
 * Created by Wensheng on 15/9/22.
 */
var albumApp = angular.module('albumApp', []);

albumApp.controller('AlbumController', ['$http','$scope', function($http, $scope){

    $scope.albumPhotoList=[];
    $scope.albumList =[];

    $http.get('/albums').then(function(response) {
        console.log(response);
        $scope.albumList = response.data.data;
    }, function(response) {
        console.log(response);
    });

    this.selectAlbum = function (id){
        var selectedAlbum = "";
        angular.forEach($scope.albumList, function(album, index){
            if(album.id === id){
                selectedAlbum = album;
            }else{
                return;
            }
        });
        if(selectedAlbum !== null && selectedAlbum !== undefined){
            this.getCorrespondedAlbum(selectedAlbum);
        }
    }

    this.getCorrespondedAlbum = function(album){
        $http.get("/album/"+album.id, {id: album.id})
            .then(function(response){
                console.log(response.data.data);
                $scope.albumPhotoList = response.data.data;
                angular.forEach($scope.albumPhotoList, function(photo, index){
                    $http.get("/"+ photo.id+"/picture").then(
                        function(resp){
                            //console.log(resp.data);
                            $scope.albumPhotoList[index].url = resp.data;
                        },
                        function(resp){

                        }
                    );
                });
            },function(response){
                console.log(response.data.data);
            });
    }



}]);