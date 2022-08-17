<?php 
    require_once '../koneksi.php';
    
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
    else{
        $query = mysqli_query($connect, "SELECT * FROM menu where id_menu = '$_GET[id_menu]' AND kapasitas = 'ada' ");
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
?>