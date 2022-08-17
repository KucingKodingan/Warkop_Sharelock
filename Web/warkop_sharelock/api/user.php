<?php 
    require_once '../koneksi.php';

    if(!$_GET){
        $query = mysqli_query($connect, "SELECT * FROM user");
        $resultType = array();
        $i = 0;
        while($row = mysqli_fetch_assoc($query)){
            $resultType[$i]['id_user'] = $row['id_user'];
            $resultType[$i]['nama'] = $row['nama'];
            $resultType[$i]['nomor_wa'] = $row['nomor_wa'];
            $resultType[$i]['email'] = $row['email'];
            $resultType[$i]['password'] = $row['password'];
            $resultType[$i]['sebagai'] = $row['sebagai'];
            $resultType[$i]['shift'] = $row['shift'];
            $i++;
        }
        echo json_encode($resultType);   
    }
    else if(isset($_GET['login'])){
        $query = mysqli_query($connect, "SELECT * FROM user where
                                email= '$_GET[email]' AND 
                                password = '$_GET[password]' ");
        $resultType = array();
        $i = 0;
        while($row = mysqli_fetch_assoc($query)){
            $resultType[$i]['id_user'] = $row['id_user'];
            $resultType[$i]['nama'] = $row['nama'];
            $resultType[$i]['nomor_wa'] = $row['nomor_wa'];
            $resultType[$i]['email'] = $row['email'];
            $resultType[$i]['password'] = $row['password'];
            $resultType[$i]['sebagai'] = $row['sebagai'];
            $resultType[$i]['shift'] = $row['shift'];
            $i++;
        }
        echo json_encode($resultType);
    }
    if(isset($_GET['cek_registrasi'])){
        $query = mysqli_query($connect, "SELECT * FROM user where email='$_GET[email]' AND sebagai='$_GET[sebagai]' ");
        $resultType = array();
        $i = 0;
        while($row = mysqli_fetch_assoc($query)){
            $resultType[$i]['id_user'] = $row['id_user'];
            $resultType[$i]['nama'] = $row['nama'];
            $resultType[$i]['nomor_wa'] = $row['nomor_wa'];
            $resultType[$i]['email'] = $row['email'];
            $resultType[$i]['password'] = $row['password'];
            $resultType[$i]['sebagai'] = $row['sebagai'];
            $resultType[$i]['shift'] = $row['shift'];
            $i++;
        }
        echo json_encode($resultType);   
    }


    //POST
    if(isset($_POST['tambahUser'])){
        $nama = $_POST['nama'];
        $nomor_wa = $_POST['nomor_wa'];
        $email = $_POST['email'];
        $password = $_POST['password'];
        $sebagai = $_POST['sebagai'];
        $shift = $_POST['shift'];
        
        $query = mysqli_query($connect, "INSERT INTO user(nama,nomor_wa,email,password,sebagai,shift) 
        VALUES ('$nama','$nomor_wa', '$email','$password','$sebagai','$shift') ");

        if($query){
            echo "berhasil";
            $json = array("response" => 'success', 'status'=> 0, "message"=> "berhasil");
        }
        else{ 
            echo "gagal";
            $json = array("response" => 'success', 'status'=> 1, "message"=> "gagal");
        }    
    }
    else if(isset($_POST['updateUser'])){
        $id_user = $_POST['id_user'];
        $nama = $_POST['nama'];
        $nomor_wa = $_POST['nomor_wa'];
        $email = $_POST['email'];
        $password = $_POST['password'];
        
        $query = mysqli_query($connect,"UPDATE user SET nama='$nama', nomor_wa='$nomor_wa', email='$email', password='$password' WHERE id_user='$id_user' ");

        if($query){
            echo "berhasil";
            $json = array("response" => 'success', 'status'=> 0, "message"=> "berhasil");
        }
        else{ 
            echo "gagal";
            $json = array("response" => 'success', 'status'=> 1, "message"=> "gagal");
        }    
    }
    
?>