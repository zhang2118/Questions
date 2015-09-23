/**
 * Created by Wensheng on 15/9/22.
 */
var albumApp = angular.module('albumApp', []);

albumApp.controller('AlbumController', ['$http','$scope', function($http, $scope){

    $scope.showController={1: true, 2: false, 3:false};
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

        $scope.showController[1] = false;
        $scope.showController[2] = true;
        $scope.showController[3] = false;
        $scope.currentPageIndex = 2;
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
    $scope.currentPageIndex = 1;
    this.updateCurrentPageIndex = function(){
        if($scope.currentPageIndex <= 1) {
            return;
        }
        else {
            $scope.currentPageIndex --;
            if($scope.currentPageIndex == 2){
                $scope.selectedPhoto = {};
            }else if($scope.currentPageIndex ==1){
                $scope.selectedAlbum = {};
            }
            angular.forEach($scope.showController, function(page, index){
                if(parseInt(index) == $scope.currentPageIndex){
                    $scope.showController[parseInt(index)] = true;
                }else{
                    $scope.showController[parseInt(index)] = false;
                }

            });
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
        $scope.showController[1] = false;
        $scope.showController[2] = false;
        $scope.showController[3] = true;
        $scope.currentPageIndex = 3;
        $scope.selectedPhoto = photo;
    }
    $scope.currentComment = "";

    this.deleteAlbum = function(){
        $http.delete("/album?id="+$scope.selectedAlbum.id);
    }

    this.submitComment = function(){
        $http.post("/comment?photoId="+$scope.selectedPhoto.id+"&comment="+$scope.currentComment)
            .then(function(response){
                console.log(response);
                alert("comment added")
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