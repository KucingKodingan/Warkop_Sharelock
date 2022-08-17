<?php 
    require_once '../koneksi.php';
    
    $hargaTerendah = 50000;
    $hargaTertinggi = 0;
    
    $query = mysqli_query($connect, "SELECT * FROM menu WHERE kategori='$_GET[kategori]' AND jenis_menu='$_GET[jenis_menu]' ");
        $resultType = array();
        $i = 0;
        while($row = mysqli_fetch_assoc($query)){
            $resultType[$i]['harga'] = $row['harga'];
            if($hargaTerendah>$row['harga']){
                $hargaTerendah = $row['harga'];
            }
            if($hargaTertinggi<$row['harga']){
                $hargaTertinggi = $row['harga'];
            }
        }

        echo $hargaTerendah."<br>";
        echo $hargaTertinggi;

    if($_GET){
        $jenis_menu = $_GET['jenis_menu'];
        $query = mysqli_query($connect, "SELECT * FROM menu WHERE jenis_menu='$jenis_menu' ");
        $resultType = array();
        $i = 0;
        while($row = mysqli_fetch_assoc($query)){
            $resultType[$i]['harga'] = $row['harga'];
            $resultType[$i]['email'] = $row['email'];
            $resultType[$i]['menu'] = $row['menu'];
            $resultType[$i]['harga'] = $row['harga'];
            $resultType[$i]['jumlah'] = $row['jumlah'];
            $resultType[$i]['total'] = $row['total'];
            $resultType[$i]['kasir'] = $row['kasir'];
            $resultType[$i]['keterangan'] = $row['keterangan'];
            $i++;
        }
        echo json_encode($resultType);   
    }
?>