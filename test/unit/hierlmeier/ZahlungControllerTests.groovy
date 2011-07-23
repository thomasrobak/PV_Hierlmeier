package hierlmeier



import org.junit.*
import grails.test.mixin.*


@TestFor(ZahlungController)
@Mock(Zahlung)
class ZahlungControllerTests {


    @Test
    void testIndex() {
        controller.index()
        assert "/zahlung/list" == response.redirectedUrl
    }

    @Test
    void testList() {

        def model = controller.list()

        assert model.zahlungInstanceList.size() == 0
        assert model.zahlungInstanceTotal == 0

    }

    @Test
    void testCreate() {
       def model = controller.create()

       assert model.zahlungInstance != null


    }

    @Test
    void testSave() {
        controller.save()

        assert model.zahlungInstance != null
        assert view == '/zahlung/create'

        // TODO: Populate valid properties

        controller.save()

        assert response.redirectedUrl == '/zahlung/show/1'
        assert controller.flash.message != null
        assert Zahlung.count() == 1
    }


    @Test
    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/zahlung/list'


        def zahlung = new Zahlung()

        // TODO: populate domain properties

        assert zahlung.save() != null

        params.id = zahlung.id

        def model = controller.show()

        assert model.zahlungInstance == zahlung
    }

    @Test
    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/zahlung/list'


        def zahlung = new Zahlung()

        // TODO: populate valid domain properties

        assert zahlung.save() != null

        params.id = zahlung.id

        def model = controller.edit()

        assert model.zahlungInstance == zahlung
    }

    @Test
    void testUpdate() {

        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/zahlung/list'

        response.reset()


        def zahlung = new Zahlung()

        // TODO: populate valid domain properties

        assert zahlung.save() != null

        // test invalid parameters in update
        params.id = zahlung.id

        controller.update()

        assert view == "/zahlung/edit"
        assert model.zahlungInstance != null

        zahlung.clearErrors()

        // TODO: populate valid domain form parameter
        controller.update()

        assert response.redirectedUrl == "/zahlung/show/$zahlung.id"
        assert flash.message != null
    }

    @Test
    void testDelete() {
        controller.delete()

        assert flash.message != null
        assert response.redirectedUrl == '/zahlung/list'

        response.reset()

        def zahlung = new Zahlung()

        // TODO: populate valid domain properties
        assert zahlung.save() != null
        assert Zahlung.count() == 1

        params.id = zahlung.id

        controller.delete()

        assert Zahlung.count() == 0
        assert Zahlung.get(zahlung.id) == null
        assert response.redirectedUrl == '/zahlung/list'


    }


}