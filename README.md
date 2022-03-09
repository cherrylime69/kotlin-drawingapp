## 👀 학습 정리

#### 👉 태블릿 사이즈 추가하기

- res ->
- alt +insert ->
- Android Resource Directory ->
- 기준에 맞는 화면 크기와 orientation 추가하기(공식 문서에 따라 width 600dp와 landscape) ->
- 레이아웃 이름은 반드시 동일하게!

#### 👉 사각형 뷰와 팩토리 구현

##### 사각형 뷰

1. 사각형 뷰 클래스에는 사각형의 속성들을 모두 정의하고 private을 통해 외부에서 접근할 수 없도록 함
2. 해당 속성에 대한 접근은 set 메서드와 get 메서드를 통해서만 가능

##### 사각형 뷰 팩토리

> 팩토리 패턴이란?
>> 클래스의 인스턴스를 대신 만들어주는 팩토리 클래스를 사용하는 패턴

1. 각각의 속성을 정의해주는 메서드를 통해 랜덤값을 넘겨주도록 구현
2. 요구 사항에 따라 view에 대한 참조없이 구현하기 위해 뷰를 생성하는 메서드는 MainActivity로 리팩토링
3. 너비와 높이를 150dp와 120dp로 설정해야 하는데, 코드를 통해서는 px값만 할당할 수 있었음. 그래서 앱을 실행시킬 때, 디바이스의 해상도를 구해 dp를 px로
   변환하는 메서드를 구현하고 활용함

```
private fun dp2px(dp: Float): Float {
        val resources = mainActivity.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
```

#### 👉 버튼 추가하기

1. 미션과 똑같은 버튼을 추가하고 싶었으나 도저히 찾지 못해서 최대한 유사하게 버튼을 구현함

#### 👉 Layout의 Depth 줄이기

- ConstranintLayout은 유연하게 뷰들을 배치할 수 있어 Depth를 줄일 수 있다!
- Depth를 늘리는 이점을 생각했을 때 거의 없다고 생각한다.

#### 👉 MVP 모델 구현하기

1. View
    1. MainActivity와 CustomView를 View로 구현
2. Presenter
    1. Presenter 클래스를 Presenter로 구현
3. Model
    1. Plane 클래스를 Model로 구현
4. Contract
    1. View와 Presenter를 연결시켜주는 인터페이스. 구글 샘플 코드를 최대한 따라하려고 했음
5. RectangleListener
    1. Model에서 데이터가 변경되면 이를 Presenter에 알려주는 옵저버 인터페이스

#### 👉 옵저버 패턴 구현하기

1. 옵저버 패턴은 이벤트가 발생하는 클래스에서 이벤트가 발생하면 이를 감지할 수 있게 해주는 역할을 한다
2. 여기서는 Model에서 데이터가 변경(이벤트 발생)되면 이 변경을 감지하여 Presenter(이벤트를 수신)에게 전달하여 지정된 메서드를 구현하도록 함
3. 학습하면서 느낀점
    1. 옵저버 패턴에서 이벤트를 발생되는 클래스는 생성자로 옵저버 인터페이스를 지정함
    2. 이벤트를 수신하는 클래스는 타입이 listener가 되야 한다

> 결과
>> ![](../../../image/Android/Week09_drawingapp/3.gif)

#### 👉 권한 요청하기

##### 권한 유형

- 권한에는 설치 시간 권한, 런타임 권한, 특별 권한을 비롯하여 권한을 다양한 유형으로 분류되어 있다.
    1. 설치 시간 권한
        - 이용자의 제한된 데이터에 제한적으로 접근하기 위한 권한으로 설치할 때 해당 권한에 대한 승인을 얻는다.
    2. 일반 권한
        - 일반 권한은 설치 시간 권한에서 확장된 데이터에 접근하기 위한 권한으로, AndroidManifest.xml 설정 파일에 명세하면 설치 시 사용자에게 권한 승인을
          묻는 팝업창을 보여준다. 인터넷 사용, 알람 설정 등이 일반 권한에 포함된다.
    3. 서명 권한
        - 앱에서 다른 앱이 정의한 서명 권한을 선언하고 두 앱이 동일한 인증서로 서명되었다면 시스템에서는 설치 시간에 첫 번째 앱에 권한을 부여합니다.
    4. 런타임 권한(위험 권한)
        - 런타임 권한(위험한 권한이라고도 함)으로 앱에서는 제한된 데이터에 추가로 액세스하고 시스템과 다른 앱에 좀 더 큰 영향을 미치는 제한된 작업을 실행합니다.
        - 즉, 이용자의 민감한 정보(위치, 연락처 정보 등)에 접근하기 위해 런타임에 요청하는 권한을 뜻함
    5. 특별 권한
        - 특별 권한은 특정 앱 작업에 상응합니다. 플랫폼과 OEM만이 특별 권한을 정의할 수 있습니다. 또한 플랫폼과 OEM은 일반적으로 다른 앱 위에 그리기와 같이 특히
          강력한 작업에 대한 액세스를 보호하려고 할 때 특별 권한을 정의한다.

##### 권한 선언하기

1. 앱에서 권한을 얻기 위해서는 먼저 권한을 선언해야 한다.
    - 카메라와 같이 하드웨어가 필요한 기능에 대한 권한에 대해서는 선택 사항으로 선언해야 한다.
        - 또한 API 수준별로 권한을 선언할 수도 있다.
    - [앱 권한 선언](https://developer.android.com/training/permissions/declaring)
2. 선언하고 싶은 권한을 `AndroidManifest.xml 파일`에 선언한다.
   ```
   <uses-permission android:name="android.permission.ACCESS_MEDIA_LOCATION"/>
   ```

##### 권한 요청하는 상황

- 권장 사항 : 해당 권한이 이용하는 앱에서 왜 요청하고 있는지 이유를 설명하면 이용자가 쉽게 승인을 한다. 따라서 권한이 필요한 이유를 요청 전에 먼저 보여주도록 하는 걸 권장

1. 사용자가 권한을 승인 했는지 확인한다. -> `checkSelfPermission 메서드` 사용
    - 이 메서드는 앱에 권한이 있는지에 따라 PERMISSION_GRANTED 또는 PERMISSION_DENIED를 반환합니다.
    - ContextCompat.checkSelfPermission() 메서드가 PERMISSION_DENIED를
      반환하면 `shouldShowRequestPermissionRationale()`을 호출하자. 이 메서드가 true를 반환하면 교육용 UI를 사용자에게 표시합니다. 이
      UI에서 사용자가 사용 설정하려는 기능에 특정 권한이 필요한 이유를 설명합니다.
    - 해당 권한이 왜 앱에 필요한지 메세지를 출력하여 승인을 유도하자
2. 승인을 하지 않았다면 승인 요청을 한다. -> `requestPermissions 메서드` 사용
3. 사용자가 승인이나 거부를 하면 -> `onRequestPermissionResult 메서드`를 사용한다.
4. 여러 권한을 동시에 요청하려면 -> `RequestMultiplePermissions`를 사용
5. 권한 요청을 계약으로 만들고 싶다면 ->

```
val requestPermissionLauncher =
    registerForActivityResult(RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted. Continue the action or workflow in your
            // app.
        } else {
            // Explain to the user that the feature is unavailable because the
            // features requires a permission that the user has denied. At the
            // same time, respect the user's decision. Don't link to system
            // settings in an effort to convince the user to change their
            // decision.
        }
    }
```

- 출처
    - [velog](https://velog.io/@jonmad/TIl.-%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%95%B1-%EA%B6%8C%ED%95%9C%EA%B3%BC-%EC%82%AC%EC%9A%A9-%EC%B9%B4%EB%A9%94%EB%9D%BC%ED%95%98%EA%B8%B0)
    - [공식문서](https://developer.android.com/training/permissions/requesting)

#### 👉 사진 모델 설계하기

##### 설계 방향

- 기존에 있던 Rectangle 모델을 인터페이스로 바꾸고 이를 다시 Normal Rectangle과 Rectangle를 상속하고 비트맵 속성만을 추가한
  PhotoRectangle을 생성하여 코드의 재사용성을 높임
- `run` 함수를 통해 PhotoRectangle이면 비트맵 값을 주고 아니면 null 값을 할당함

```
photo?.run {
            val photoRect = PhotoRectangle()
            photoRect.setBitmap(this)
            newRect = photoRect
        } ?: run {
            newRect = NormalRectangle()
        }
```

- `is 연산자`를 통해 Rectangle이 PhotoRectangle이면 비트맵을 그리도록 하고 아니면 Rect를 그리도록 함

```
if (it is PhotoRectangle) {
                val photo: Bitmap = it.getPhoto() ?: return
                val rect = RectF(x, y, width, height)
                canvas?.drawBitmap(photo, null, rect, paint)
            } else {
                canvas?.drawRect(x, y, width, height, paint)
            }
```

#### 👉 미디어에서 선택한 이미지를 Uri로 불러오고 이를 비트맵으로 변환하기
- Intent의 결과값으로 Uri를 받아오도록 계약을 구현함
- 정상적으로 이미지를 선택했다면 `ImageDecoder.decodeBitmap` 으로 Uri를 비트맵으로 변환함
  - 위의 방법은 SDK가 23이상만 가능. 23 미만은 `MediaStore.Images.Media.getBitmap()` 메서드를 활용함
```
registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val imageUri = it.data?.data ?: throw IllegalAccessError("사진을 선택해야 합니다.")
                val photo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                            this.contentResolver,
                            imageUri
                        )
                    )
                } else {
                    MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                }
                val width = dp2px(150f)
                val height = dp2px(120f)
                presenter.addNewRectangle(width, height, photo)
            } else {
                Snackbar.make(customView, "사진을 불러오지 못하였습니다", Snackbar.LENGTH_SHORT).show()
            }
        }
```

> 결과
>> ![](../../../image/Android/Week09_drawingapp/5.gif)