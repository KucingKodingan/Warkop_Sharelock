<?php 
    require_once '../koneksi.php';
    
    if($_GET){
        $kategori = $_GET['kategori'];
        $jenis_menu = $_GET['jenis_menu'];
        $nominal_awal = $_GET['nominal_awal'];
        $nominal_batas = $_GET['nominal_batas'];

        $query = mysqli_query($connect, "SELECT * FROM pesanan ORDER BY id_menu ASC");
        $jumlah = 0;
        while($row = mysqli_fetch_assoc($query)){
            $jumlah = $row['id_menu'];
        }
        $id = 0;
        $nilai_terbesar = 0;
        for($i = 1; $i<=$jumlah; $i++){
            $jumlah_keseluruhan = 0;
            $query = mysqli_query($connect, "SELECT * FROM pesanan where id_menu='$i' 
                                            AND kategori='$kategori' AND jenis_menu='$jenis_menu'
                                            AND harga BETWEEN $nominal_awal AND $nominal_batas ");
            while($row = mysqli_fetch_assoc($query)){
                $jumlah_keseluruhan += $row['jumlah'];
            }
            if($nilai_terbesar<$jumlah_keseluruhan){
                $id = $i;
                $nilai_terbesar = $jumlah_keseluruhan;
            }
        }
        $resultType = array();

        $queryFinal = mysqli_query($connect, "SELECT * FROM menu where id_menu = '$id' ");

        if($id==0){
            $queryFinal = mysqli_query($connect,"SELECT * FROM menu WHERE kategori='$kategori' AND jenis_menu='$jenis_menu'
                                                AND harga BETWEEN $nominal_awal AND $nominal_batas ");
        }
        
        $resultType = array();
        $i = 0;
        while($row = mysqli_fetch_assoc($queryFinal)){
            $resultType[$i]['id_menu'] = $row['id_menu'];
            $resultType[$i]['nama_menu'] = $row['nama_menu'];
            $resultType[$i]['kategori'] = $row['kategori'];
            $resultType[$i]['jenis_menu'] = $row['jenis_menu'];
            $resultType[$i]['harga'] = $row['harga'];
            $resultType[$i]['gambar'] = $row['gambar'];
            $resultType[$i]['kapasitas'] = $row['kapasitas'];
            $resultType[$i]['jumlah_pesanan'] = $nilai_terbesar;
            $resultType[$i]['nominal_awal'] = $nominal_awal;
            $resultType[$i]['nominal_batas'] = $nominal_batas;
            $i++;
        }
        echo json_encode($resultType);
    }

?>