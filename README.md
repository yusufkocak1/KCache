# # K-CACHE Kullanım Kılavuzu

## Projeye K-CACHE Eklenmesi
K-CACHE, Maven projenize bir bağımlılık olarak eklenebilir. Bunun için `pom.xml` dosyanıza aşağıdaki satırları ekleyin:

```xml
<dependency>
    <groupId>com.kocak</groupId>
    <artifactId>K-CACHE</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

Eğer bu bağımlılığı GitHub Package Registry üzerinden alıyorsanız, ilgili repository ayarlarının `settings.xml` dosyanızda tanımlı olduğundan emin olun.

## Kullanım Örneği
### 1. Anotasyon Kullanımı
K-CACHE, Spring uygulamanızda kolayca kullanılabilir. Bir metodu cachelemek için `@KCacheable` anotasyonunu kullanabilirsiniz.

**Örnek Kullanım:**

```java
import com.kocak.kcache.annotation.KCacheable;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    @KCacheable(cacheName = "exampleCache", key = "#id")
    public String getData(String id) {
        // Burada ağır bir işlem simüle edilebilir.
        return "Data for ID: " + id;
    }
}
```

Bu örnekte, `getData` metodu çağrıldığında sonuç cache'e kaydedilir. Aynı parametrelerle yapılan sonraki çağrılar doğrudan cache'den döndürülür.

### 2. Cache Yönetimi
K-CACHE, cache yönetimi için bir web arayüzü sunar. Uygulama çalıştırıldığında, aşağıdaki URL üzerinden cache durumunu görüntüleyebilir ve cache temizleme işlemleri yapabilirsiniz:

```
http://localhost:8080/kcache
```

Arayüzde şunları yapabilirsiniz:
- Mevcut cache'leri görüntüleme
- Belirli bir cache'i temizleme
- Tüm cache'leri temizleme

### 3. Programatik Cache Yönetimi
K-CACHE, programatik olarak cache işlemlerini yönetmenize olanak tanır.

**Örnek:**

```java
import com.kocak.kcache.manager.KCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CacheAdmin {

    @Autowired
    private KCacheManager cacheManager;

    public void clearSpecificCache(String cacheName) {
        cacheManager.clearCache(cacheName);
    }

    public void clearAllCaches() {
        cacheManager.clearAllCaches();
    }
}
```

Bu örnekte, `CacheAdmin` sınıfı belirli bir cache'i veya tüm cache'leri temizlemek için kullanılabilir.

## Konfigürasyon
### application.properties Ayarları
K-CACHE için özelleştirilmiş ayarları `application.properties` dosyasına ekleyebilirsiniz:

```properties
kcache.default-ttl=3600  # Cache ömrü (saniye cinsinden)
kcache.max-entries=100   # Maksimum giriş sayısı
```

## Sürüm Bilgileri
- **Sürüm:** 1.0.0-SNAPSHOT
- **Java Versiyonu:** 21
- **Spring Framework Versiyonu:** 3.4.2

## Hata Ayıklama
K-CACHE ile ilgili bir sorunla karşılaşırsanız, aşağıdaki adımları izleyebilirsiniz:
1. **Logları Kontrol Edin:**
    - `application.properties` dosyasına şu ayarı ekleyerek K-CACHE loglarını etkinleştirebilirsiniz:
   ```properties
   logging.level.com.kocak.kcache=DEBUG
   ```

2. **GitHub Bağımlılığı:**
    - `settings.xml` dosyasındaki GitHub token ayarlarınızı kontrol edin.

