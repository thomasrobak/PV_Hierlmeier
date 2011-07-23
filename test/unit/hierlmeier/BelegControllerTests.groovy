package hierlmeier



import org.junit.*
import grails.test.mixin.*


@TestFor(BelegController)
@Mock(Beleg)
class BelegControllerTests {


    @Test
    void testIndex() {
        controller.index()
        assert "/beleg/list" == response.redirectedUrl
    }

    @Test
    void testList() {

        def model = controller.list()

        assert model.belegInstanceList.size() == 0
        assert model.belegInstanceTotal == 0

    }

    @Test
    void testCreate() {
       def model = controller.create()

       assert model.belegInstance != null


    }

    @Test
    void testSave() {
        controller.save()

        assert model.belegInstance != null
        assert view == '/beleg/create'

        // TODO: Populate valid properties

        controller.save()

        assert response.redirectedUrl == '/beleg/show/1'
        assert controller.flash.message != null
        assert Beleg.count() == 1
    }


    @Test
    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/beleg/list'


        def beleg = new Beleg()

        // TODO: populate domain properties

        assert beleg.save() != null

        params.id = beleg.id

        def model = controller.show()

        assert model.belegInstance == beleg
    }

    @Test
    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/beleg/list'


        def beleg = new Beleg()

        // TODO: populate valid domain properties

        assert beleg.save() != null

        params.id = beleg.id

        def model = controller.edit()

        assert model.belegInstance == beleg
    }

    @Test
    void testUpdate() {

        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/beleg/list'

        response.reset()


        def beleg = new Beleg()

        // TODO: populate valid domain properties

        assert beleg.save() != null

        // test invalid parameters in update
        params.id = beleg.id

        controller.update()

        assert view == "/beleg/edit"
        assert model.belegInstance != null

        beleg.clearErrors()

        // TODO: populate valid domain form parameter
        controller.update()

        assert response.redirectedUrl == "/beleg/show/$beleg.id"
        assert flash.message != null
    }

    @Test
    void testDelete() {
        controller.delete()

        assert flash.message != null
        assert response.redirectedUrl == '/beleg/list'

        response.reset()

        def beleg = new Beleg()

        // TODO: populate valid domain properties
        assert beleg.save() != null
        assert Beleg.count() == 1

        params.id = beleg.id

        controller.delete()

        assert Beleg.count() == 0
        assert Beleg.get(beleg.id) == null
        assert response.redirectedUrl == '/beleg/list'


    }


}