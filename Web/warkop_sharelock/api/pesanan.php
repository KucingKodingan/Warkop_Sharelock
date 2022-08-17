<?php 
    require_once '../koneksi.php';

    //GET

    if(!$_GET){
        $query = mysqli_query($connect, "SELECT * FROM pesanan");
        $resultType = array();
        $i = 0;
        while($row = mysqli_fetch_assoc($query)){
            $resultType[$i]['id_pesanan'] = $row['id_pesanan'];
            $resultType[$i]['email'] = $row['email'];
            $resultType[$i]['menu'] = $row['menu'];
            $resultType[$i]['jenis_menu'] = $row['jenis_menu'];
            $resultType[$i]['harga'] = $row['harga'];
            $resultType[$i]['jumlah'] = $row['jumlah'];
            $resultType[$i]['total'] = $row['total'];
            // $resultType[$i]['kasir'] = $row['kasir'];
            $resultType[$i]['keterangan'] = $row['keterangan'];

            //Jika belum ada pesanan
            
            $i++;
        }
        echo json_encode($resultType);   
    }
    else if(isset($_GET['keterangan'])){
        $query = mysqli_query($connect, "SELECT * FROM pesanan where email='$_GET[email]' AND keterangan='$_GET[keterangan]' ");
        $resultType = array();
        $i = 0;
        while($row = mysqli_fetch_assoc($query)){
            $resultType[$i]['id_pesanan'] = $row['id_pesanan'];
            $resultType[$i]['email'] = $row['email'];
            $resultType[$i]['menu'] = $row['menu'];
            $resultType[$i]['jenis_menu'] = $row['jenis_menu'];
            $resultType[$i]['harga'] = $row['harga'];
            $resultType[$i]['jumlah'] = $row['jumlah'];
            $resultType[$i]['total'] = $row['total'];
            // $resultType[$i]['kasir'] = $row['kasir'];
            $resultType[$i]['keterangan'] = $row['keterangan'];
            $i++;
        }
        echo json_encode($resultType);
    }
    else if(isset($_GET['jumlah_menu'])){
        $query = mysqli_query($connect, "SELECT * FROM pesanan where email='$_GET[email]' AND id_menu='$_GET[id_menu]' AND keterangan='belum pesan' ");
        $resultType = array();
        $i = 0;
        while($row = mysqli_fetch_assoc($query)){
            $i += $row['jumlah'];
        }
        $resultType[0]['jumlah'] = $i;
        echo json_encode($resultType);
    }
    else if(isset($_GET['list_pesanan_pelanggan'])){
        //$query = mysqli_query($connect, "SELECT * FROM pesanan WHERE email='$_GET[email]' AND keterangan='belum //bayar' AND id_pesanan>0 GROUP BY id_pesanan HAVING count(*) > 1 ORDER BY id_pesanan ASC ");
        
        
        $query = mysqli_query($connect, "SELECT * FROM pesanan WHERE email='$_GET[email]' AND keterangan='belum bayar' AND id_pesanan>0 GROUP BY id_pesanan HAVING count(*) > 0 ORDER BY id_pesanan ASC ");
        
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
    else if(isset($_GET['list_pesanan_pelanggan_sudah_bayar'])){
//        $query = mysqli_query($connect, "SELECT * FROM pesanan WHERE email='$_GET[email]' AND keterangan='sudah //bayar' AND id_pesanan>0 GROUP BY id_pesanan HAVING count(*) > 1 ORDER BY id_pesanan ASC ");
        
        
        $query = mysqli_query($connect, "SELECT * FROM pesanan WHERE email='$_GET[email]' AND keterangan='sudah bayar' AND id_pesanan>0 GROUP BY id_pesanan HAVING count(*) > 0 ORDER BY id_pesanan ASC ");
        
        
        
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
    else if(isset($_GET['list_pesanan_pelanggan_kasir'])){
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
    else if(isset($_GET['list_pesanan_pelanggan_kasir_sudah_bayar'])){
        $query = mysqli_query($connect, "SELECT * FROM pesanan WHERE keterangan='sudah bayar' AND id_pesanan>0 GROUP BY id_pesanan HAVING count(*) > 1 ORDER BY id_pesanan ASC");
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
    else if(isset($_GET['profile_pesanan'])){
        $query = mysqli_query($connect, "SELECT * FROM pesanan WHERE id_pesanan='$_GET[id_pesanan]' AND email='$_GET[email]' ");
        $resultType = array();
        $i = 0;
        
        while($row = mysqli_fetch_assoc($query)){
            $resultType[$i]['menu'] = $row['menu'];
            $resultType[$i]['kasir'] = $row['kasir'];
            $resultType[$i]['jumlah'] = $row['jumlah'];
            $resultType[$i]['harga'] = $row['harga'];
            $resultType[$i]['total'] = $row['total'];
            $resultType[$i]['tgl_pesanan'] = $row['tgl_pesanan'];

            $i++;
        }
        echo json_encode($resultType);
    }
    else{
        $query = mysqli_query($connect, "SELECT * FROM pesanan where email='$_GET[email]' ");
        $resultType = array();
        $i = 0;
        while($row = mysqli_fetch_assoc($query)){
            $resultType[$i]['id_pesanan'] = $row['id_pesanan'];
            $resultType[$i]['email'] = $row['email'];
            $resultType[$i]['menu'] = $row['menu'];
            $resultType[$i]['jenis_menu'] = $row['jenis_menu'];
            $resultType[$i]['harga'] = $row['harga'];
            $resultType[$i]['jumlah'] = $row['jumlah'];
            $resultType[$i]['total'] = $row['total'];
            // $resultType[$i]['kasir'] = $row['kasir'];
            $resultType[$i]['keterangan'] = $row['keterangan'];
            $i++;
        }
        echo json_encode($resultType);
    }
    

    //POST
    if(isset($_POST['postPesanan'])){
        $email = $_POST['email'];
        $nama = $_POST['nama'];
        $id_menu = $_POST['id_menu'];
        $menu = $_POST['menu'];
        $kategori = $_POST['kategori'];
        $jenis_menu = $_POST['jenis_menu'];
        $harga = $_POST['harga'];
        $jumlah = $_POST['jumlah'];
        $total = $_POST['total'];
        $kasir = $_POST['kasir'];
        $keterangan = $_POST['keterangan'];
        
        $query = mysqli_query($connect, "INSERT INTO pesanan(email,nama,id_menu,menu,kategori,jenis_menu,harga,jumlah,total,kasir,keterangan) 
        VALUES ('$email','$nama','$id_menu', '$menu','$kategori','$jenis_menu','$harga','$jumlah','$total','$kasir','$keterangan') ");

        if($query){
            echo "berhasil";
            $json = array("response" => 'success', 'status'=> 0, "message"=> "berhasil");
        }
        else{ 
            echo "gagal";
            $json = array("response" => 'success', 'status'=> 1, "message"=> "gagal");
        }    
    }
    else if(isset($_POST['memesan'])){
        $id_pesanan = $_POST['id_pesanan'];
        $email = $_POST['email'];

        $query = mysqli_query($connect, "SELECT * FROM pesanan ORDER BY id_pesanan ASC");
        $resultType = array();
        $i = 0;
        while($row = mysqli_fetch_assoc($query)){
            $i = $row['id_pesanan'];
        }

        //Jika belum ada data
        if($i==0){
            $i = 9;
        }

        $i++;
        
        $query = mysqli_query($connect,"UPDATE pesanan set keterangan='belum bayar', id_pesanan='$i', tgl_pesanan=CURRENT_TIMESTAMP()
                                        WHERE id_pesanan='' AND email='$email' ");

        if($query){
            echo "berhasil";
            $json = array("response" => 'success', 'status'=> 0, "message"=> "berhasil");
        }
        else{ 
            echo "gagal";
            $json = array("response" => 'success', 'status'=> 1, "message"=> "gagal");
        }    
    }
    else if(isset($_POST['updatePesanan'])){
        $jumlah = $_POST['jumlah'];
        $total = $_POST['total'];
        $id_menu = $_POST['id_menu'];
        $email = $_POST['email'];

        $query = mysqli_query($connect,"UPDATE pesanan SET jumlah='$jumlah', total='$total' WHERE id_menu='$id_menu' AND keterangan='belum pesan' AND email='$email' ");

        if($query){
        echo "berhasil";
            $json = array("response" => 'success', 'status'=> 0, "message"=> "berhasil");
        }
        else{ 
            echo "gagal";
            $json = array("response" => 'success', 'status'=> 1, "message"=> "gagal");
        }    
    }
    else if(isset($_POST['deletePesanan'])){
        $email = $_POST['email'];
        $id_menu = $_POST['id_menu'];
        
        $query = mysqli_query($connect, "DELETE FROM pesanan WHERE email='$email' AND id_menu='$id_menu' ");

        if($query){
            echo "berhasil";
            $json = array("response" => 'success', 'status'=> 0, "message"=> "berhasil");
        }
        else{ 
            echo "gagal";
            $json = array("response" => 'success', 'status'=> 1, "message"=> "gagal");
        }    
    }
    else if(isset($_POST['updateBayarPesanan'])){
        $id_pesanan = $_POST['id_pesanan'];
        $nama_kasir = $_POST['nama_kasir'];

        $query = mysqli_query($connect,"UPDATE pesanan SET keterangan='sudah bayar', kasir='$nama_kasir' WHERE id_pesanan='$id_pesanan' ");

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