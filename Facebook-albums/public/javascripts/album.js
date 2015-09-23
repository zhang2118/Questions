/**
 * Created by Wensheng on 15/9/22.
 */
var albumApp = angular.module('albumApp', []);

albumApp.controller('AlbumController', ['$http','$scope', function($http, $scope){

    //Javascript object used to control hide and show of div element
    $scope.showController={1: true, 2: false, 3:false};

    //PhotoList of the selected album.
    $scope.albumPhotoList=[];

    //The album list of the user.
    $scope.albumList =[];

    //The selected album.
    $scope.selectedAlbum = {};

    //Get operation on the server, in order to retrieve all albums.
    $http.get('/albums').then(function(response) {
        console.log(response);
        $scope.albumList = response.data;
    }, function(response) {
        console.log(response);
    });


    //Method will be invoked when user select an album.
    this.selectAlbum = function (id){
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

    //Indicator of the page number, which is used to control hide and show of views.
    $scope.currentPageIndex = 1;

    //When user hit back button, update indicator of page number.
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

    //Aiming to provide update operation to corresponded album, but not working for now.
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

    //Invoked when user selected a photo.
    this.selectPhoto = function(photo){
        console.log(photo);
        $scope.showController[1] = false;
        $scope.showController[2] = false;
        $scope.showController[3] = true;
        $scope.currentPageIndex = 3;
        $scope.selectedPhoto = photo;
    }

    //The last comment the user entered. This is the comment on photos.
    $scope.currentComment = "";

    //Aiming to provide deletion operation to user, not workign for now
    this.deleteAlbum = function(){
        $http.delete("/album?id="+$scope.selectedAlbum.id);
    }

    //Submit comment on photo and publish on Facebook.
    this.submitComment = function(){
        $http.post("/comment?photoId="+$scope.selectedPhoto.id+"&comment="+$scope.currentComment)
            .then(function(response){
                console.log(response);
                alert("comment added")
            },function(response){
                console.log(response);
        });
    }

    //Get photo list of a specific album.
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