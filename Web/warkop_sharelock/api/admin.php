<?php 

    require_once '../koneksi.php';
    
    //Get
    if(!$_GET){
        $query = mysqli_query($connect, "SELECT * FROM menu");
        $resultType = array();
        $i = 0;
        while($row = mysqli_fetch_assoc($query)){
            $resultType[$i]['id_menu'] = $row['id_menu'];
            $resultType[$i]['nama_menu'] = $row['nama_menu'];
            $resultType[$i]['kategori'] = $row['kategori'];
            $resultType[$i]['jenis_menu'] = $row['jenis_menu'];
            $resultType[$i]['harga'] = $row['harga'];
            $resultType[$i]['gambar'] = $row['gambar'];
            $resultType[$i]['kapasitas'] = $row['kapasitas'];
            $i++;
        }
        echo json_encode($resultType);   
    }
    else if(isset($_GET['admin_list_pesanan_pelanggan_belum_pesan'])){
        $query = mysqli_query($connect, "SELECT * FROM pesanan WHERE keterangan='belum pesan' ");
        $resultType = array();
        $email = "";
        $i = 0;
        $jumlah = 0;
        $totalHarga = 0;

        while($row = mysqli_fetch_assoc($query)){
            if($email != $row['email']){
                
                $email = $row['email'];

                $queryFinal = mysqli_query($connect, "SELECT * FROM pesanan WHERE email='$row[email]' ");

                while($row2 = mysqli_fetch_assoc($queryFinal)){
                    $jumlah += $row2['jumlah'];
                    $totalHarga += $row2['total']; 
                }
                $resultType[$i]['id_pesanan'] = $row['id_pesanan'];
                $resultType[$i]['email'] = $row['email'];
                $resultType[$i]['nama'] = $row['nama'];
                $resultType[$i]['jumlah'] = $jumlah;
                $resultType[$i]['total'] = $totalHarga;
                
                $i++;
                $jumlah = 0;
                $totalHarga = 0;    

            }
        }

        echo json_encode($resultType);
        
        
        // while($row = mysqli_fetch_assoc($query)){
            // $query2 = mysqli_query($connect, "SELECT * FROM pesanan WHERE email='$row[email]' ");

            // while($row2 = mysqli_fetch_assoc($query2)){
            //     $jumlah += $row2['jumlah'];
            //     $totalHarga += $row2['total'];
            // }

            // $resultType[$i]['id_pesanan'] = $row['id_pesanan'];
            // $resultType[$i]['nama'] = $row['nama'];
            // $resultType[$i]['jumlah'] = $jumlah;
            // $resultType[$i]['total'] = $totalHarga;
            // $resultType[$i]['tgl_pesanan'] = $row['tgl_pesanan'];

        //     $i++;
        //     $jumlah = 0;
        //     $totalHarga = 0;

        // }
        // echo json_encode($resultType);
    }
    else if(isset($_GET['admin_list_pesanan_pelanggan_belum_bayar'])){
        $query = mysqli_query($connect, "SELECT * FROM pesanan WHERE keterangan='belum bayar' AND id_pesanan>0 GROUP BY id_pesanan HAVING count(*) > 1 ORDER BY id_pesanan ASC ");
        $resultType = array();
        $i = 0;

        $jumlah = 0;
        $totalHarga = 0;
        
        while($row = mysqli_fetch_assoc($query)){
            $query2 = mysqli_query($connect, "SELECT * FROM pesanan WHERE id_pesanan='$row[id_pesanan]' ");

            while($row2 = mysqli_fetch_assoc($query2)){
                $jumlah += $row2['jumlah'];
                $totalHarga += $row2['total'];
            }

            $resultType[$i]['id_pesanan'] = $row['id_pesanan'];
            $resultType[$i]['email'] = $row['email'];
            $resultType[$i]['nama'] = $row['nama'];
            $resultType[$i]['jumlah'] = $jumlah;
            $resultType[$i]['total'] = $totalHarga;
            $resultType[$i]['tgl_pesanan'] = $row['tgl_pesanan'];

            $i++;
            $jumlah = 0;
            $totalHarga = 0;

        }
        echo json_encode($resultType);
    }
    else if(isset($_GET['admin_list_pesanan_pelanggan_sudah_bayar'])){
        $query = mysqli_query($connect, "SELECT * FROM pesanan WHERE keterangan='sudah bayar' AND id_pesanan>0 GROUP BY id_pesanan HAVING count(*) > 1 ORDER BY id_pesanan ASC ");
        $resultType = array();
        $i = 0;

        $jumlah = 0;
        $totalHarga = 0;
        
        while($row = mysqli_fetch_assoc($query)){
            $query2 = mysqli_query($connect, "SELECT * FROM pesanan WHERE id_pesanan='$row[id_pesanan]' ");

            while($row2 = mysqli_fetch_assoc($query2)){
                $jumlah += $row2['jumlah'];
                $totalHarga += $row2['total'];
            }

            $resultType[$i]['id_pesanan'] = $row['id_pesanan'];
            $resultType[$i]['email'] = $row['email'];
            $resultType[$i]['nama'] = $row['nama'];
            $resultType[$i]['jumlah'] = $jumlah;
            $resultType[$i]['total'] = $totalHarga;
            $resultType[$i]['tgl_pesanan'] = $row['tgl_pesanan'];

            $i++;
            $jumlah = 0;
            $totalHarga = 0;

        }
        echo json_encode($resultType);
    }
    else if(isset($_GET['admin_list_akun_kasir'])){
        $query = mysqli_query($connect, "SELECT * FROM user where sebagai='kasir' ");
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
    else if(isset($_GET['admin_list_akun_pelanggan'])){
        $query = mysqli_query($connect, "SELECT * FROM user where sebagai='pelanggan' ");
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
    else if(isset($_GET['cek_email'])){
        $query = mysqli_query($connect, "SELECT * FROM user where email='$_GET[email]' ");
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

    //Post
    if(isset($_POST['tambahMenuBaruAdmin'])){
        $gambar=$_POST['gambar'];
        $nama_menu=$_POST['nama_menu'];
        $kategori=$_POST['kategori'];
        $jenis_menu=$_POST['jenis_menu'];
        $harga=$_POST['harga'];
        $kapasitas=$_POST['kapasitas'];

        $namaFile = $_FILES['gambar']['name'];
        $error = $_FILES['gambar']['error'];
        $tmpName = $_FILES['gambar']['tmp_name'];
        
        $ekstensiGambar = explode('.', $namaFile);
        $ekstensiGambar = strtolower(end($ekstensiGambar));
        
        //Mengupload Gambar
        $namaFileBaru = $_POST['nama_menu'];
        $namaFileBaru .='.';
        $namaFileBaru .= $ekstensiGambar;

        $berhasil = move_uploaded_file($tmpName, '../image/menu/' . $namaFileBaru);

        // if($berhasil==true){
            $query = mysqli_query($connect,"INSERT INTO menu(nama_menu, kategori, jenis_menu, harga, gambar, kapasitas) 
                                            VALUES ('$nama_menu','$kategori','$jenis_menu','$harga','$namaFileBaru','$kapasitas') " );
            if($query){
                echo "berhasil";
                $json = array("response" => 'success', 'status'=> 0, "message"=> "berhasil");
            }
            else{ 
                echo "gagal";
                $json = array("response" => 'success', 'status'=> 1, "message"=> "gagal");
            }    
        // };
    }
    else if(isset($_POST['deleteMenuAdmin'])){
        
        $id_menu = $_POST['id_menu'];
        
        $query = mysqli_query($connect, "DELETE FROM menu WHERE id_menu='$id_menu' ");

        if($query){
            echo "berhasil";
            $json = array("response" => 'success', 'status'=> 0, "message"=> "berhasil");
        }
        else{ 
            echo "gagal";
            $json = array("response" => 'success', 'status'=> 1, "message"=> "gagal");
        } 
    }
    else if(isset($_POST['updateMenuAdmin'])){
        $gambar=$_POST['gambar'];
        $id_menu = $_POST['id_menu'];
        $nama_menu=$_POST['nama_menu'];
        $kategori=$_POST['kategori'];
        $jenis_menu=$_POST['jenis_menu'];
        $harga=$_POST['harga'];
        $kapasitas=$_POST['kapasitas'];

        $namaFile = $_FILES['gambar']['name'];
        $error = $_FILES['gambar']['error'];
        $tmpName = $_FILES['gambar']['tmp_name'];
        
        $ekstensiGambar = explode('.', $namaFile);
        $ekstensiGambar = strtolower(end($ekstensiGambar));
        
        //Mengupload Gambar
        $namaFileBaru = $_POST['nama_menu'];
        $namaFileBaru .='.';
        $namaFileBaru .= $ekstensiGambar;

        unlink($namaFileBaru);

        $berhasil = move_uploaded_file($tmpName, '../image/menu/' . $namaFileBaru);

        $query = mysqli_query($connect,"UPDATE menu SET nama_menu='$nama_menu', 
        kategori='$kategori', jenis_menu='$jenis_menu', harga='$harga', 
        gambar='$namaFileBaru', kapasitas='$kapasitas' WHERE id_menu='$id_menu' ");

        if($query){
            echo "berhasil";
            $json = array("response" => 'success', 'status'=> 0, "message"=> "berhasil");
        }
        else{ 
            echo "gagal";
            $json = array("response" => 'success', 'status'=> 1, "message"=> "gagal");
        } 
    }
    else if(isset($_POST['updateMenuAdminNoImage'])){
        $id_menu = $_POST['id_menu'];
        $nama_menu=$_POST['nama_menu'];
        $kategori=$_POST['kategori'];
        $jenis_menu=$_POST['jenis_menu'];
        $harga=$_POST['harga'];
        $kapasitas=$_POST['kapasitas'];

        $query = mysqli_query($connect,"UPDATE menu SET nama_menu='$nama_menu', 
        kategori='$kategori', jenis_menu='$jenis_menu', harga='$harga', kapasitas='$kapasitas' WHERE id_menu='$id_menu' ");
        
        if($query){
            echo "berhasil";
            $json = array("response" => 'success', 'status'=> 0, "message"=> "berhasil");
        }
        else{ 
            echo "gagal";
            $json = array("response" => 'success', 'status'=> 1, "message"=> "gagal");
        } 
    }
    else if(isset($_POST['tambahAkun'])){
        
        $query = mysqli_query($connect,"INSERT INTO user(nama,nomor_wa,email,password,sebagai,shift)
                                        VALUES('$_POST[nama]','$_POST[nomor_wa]','$_POST[email]','$_POST[password]','$_POST[sebagai]','$_POST[shift]') ");

        if($query){
            echo "berhasil";
            $json = array("response" => 'success', 'status'=> 0, "message"=> "berhasil");
        }
        else{ 
            echo "gagal";
            $json = array("response" => 'success', 'status'=> 1, "message"=> "gagal");
        } 
    }
    else if(isset($_POST['updateAkun'])){
        $query = mysqli_query($connect,"UPDATE user SET nama = '$_POST[nama]',
                                                        nomor_wa = '$_POST[nomor_wa]',
                                                        email = '$_POST[email]',
                                                        password = '$_POST[password]',
                                                        sebagai = '$_POST[sebagai]',
                                                        shift = '$_POST[shift]' 
                                                        WHERE id_user = '$_POST[id_user]' ");

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